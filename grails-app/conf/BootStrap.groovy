import h2gis.City

import groovy.sql.Sql
import javax.sql.DataSource
import org.geotools.factory.Hints

class BootStrap
{
  def dataSource
  def cityService


  def init = { servletContext ->
    Hints.putSystemDefault( Hints.FORCE_LONGITUDE_FIRST_AXIS_ORDER, Boolean.TRUE )

    def sql = new Sql( dataSource as DataSource)

    sql.execute( 'CREATE ALIAS IF NOT EXISTS SPATIAL_INIT FOR "org.h2gis.h2spatialext.CreateSpatialExtension.initSpatialExtension";' )
    sql.execute( 'CALL SPATIAL_INIT();' )
    sql?.close()

    if ( City.count() == 0 )
    {
      cityService.loadCSV( 'cities.csv' as File )
    }

  }

  def destroy = {
  }
}
