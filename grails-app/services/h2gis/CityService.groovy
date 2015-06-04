package h2gis

import grails.transaction.Transactional

import geoscript.GeoScript
import geoscript.feature.Schema
import geoscript.geom.Bounds
import geoscript.geom.Point
import geoscript.layer.Layer
import geoscript.render.Draw
import static geoscript.style.Symbolizers.*

import javax.imageio.ImageIO
import java.awt.image.BufferedImage


@Transactional
class CityService
{
  def messageSource
  def dataSource

  enum RenderMethod {
    BLANK, GEOSCRIPT, GEOSCRIPT_2
  }

  def loadCSV(File csvFile)
  {
    csvFile.eachLine( 0 ) { line, i ->
      if ( i > 0 )
      {

        def record = line?.split( ',' )

        def city = new City(
            name: record[0],
            country: record[1],
            population: record[2]?.toInteger(),
            capital: record[3] == 'Y',
            longitude: record[4]?.toDouble(),
            latitude: record[5]?.toDouble()
        )

//        city.location = GeoScript.unwrap( new Point( city.longitude, city.latitude ) )
//        city.location.setSRID( 4326 )
        city.location = new Point( city.longitude, city.latitude )

        if ( !city.save() )
        {
          city.errors.allErrors.each { println messageSource.getMessage( it, null ) }
        }

        //println "${city}"
      }
    }
  }

  def plotCities(def params)
  {
    //println params
    def ostream = new ByteArrayOutputStream()
    def renderMethod = RenderMethod.GEOSCRIPT
    def bbox = params['BBOX']?.split( ',' )*.toDouble()
    def imageType = params['FORMAT']?.split( '/' )?.last()
    def width = params['WIDTH'].toInteger()
    def height = params['HEIGHT'].toInteger()

    switch ( renderMethod )
    {
    case RenderMethod.BLANK:
      def image = new BufferedImage( width, height, BufferedImage.TYPE_INT_ARGB )

      ImageIO.write( image, imageType, ostream )
      break
    case RenderMethod.GEOSCRIPT:
      def cities = City.withCriteria {
        sqlRestriction "st_intersects( location, ST_MakeEnvelope(?, ?, ?, ?) )", bbox
      }*.properties

      def name = Layer.newname()
      def layer = new Layer(name, new Schema(name, [['location', 'POINT', 'epsg:4326']]))

      layer.add(cities)
      layer.style = shape( type: "star", size: 10, color: "#FF0000" )

      //println "${layer.name} ${layer.count()}"

      def renderParams = [
          size: [width, height],
          out: ostream,
          proj: params['SRS'],
          bounds: bbox as Bounds,
          format: imageType
      ]

      //println renderParams

      Draw.draw( renderParams,  layer)
      break
    case RenderMethod.GEOSCRIPT_2:

      break
    }

    [contentType: params['FORMAT'], buffer: ostream.toByteArray()]
  }
}
