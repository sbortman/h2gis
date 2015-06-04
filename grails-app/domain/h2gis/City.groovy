package h2gis

import groovy.transform.ToString
import geoscript.geom.Point
//import com.vividsolutions.jts.geom.Point
//import org.hibernate.spatial.GeometryType
import h2gis.GeometryType

@ToString( includeNames = true )
class City
{
// CITY_NAME,COUNTRY,POP,CAP,LONGITUDE,LATITUDE
  String name
  String country
  Integer population
  Boolean capital
  Double longitude
  Double latitude
  Point location


  static constraints = {
    name()
    country()
    population()
    capital()
    longitude()
    latitude()
    location(nullable: true)
  }

  static mapping = {
//    location type: GeometryType, sqlType: 'blob'
    location type: GeometryType, sqlType: 'Geometry'
  }
}
