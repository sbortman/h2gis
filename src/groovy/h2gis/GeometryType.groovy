package h2gis

import com.vividsolutions.jts.geom.Geometry
import geoscript.GeoScript
import geoscript.geom.io.WktReader
import geoscript.geom.io.WktWriter
import org.hibernate.HibernateException
import org.hibernate.engine.spi.SessionImplementor
import org.hibernate.usertype.UserType

import java.sql.PreparedStatement
import java.sql.ResultSet
import java.sql.SQLException

/**
 * Created by sbortman on 6/3/15.
 */
class GeometryType implements UserType
{
  WktReader reader = new WktReader()
  WktWriter writer = new WktWriter()

  @Override
  int[] sqlTypes()
  {
    return [1111] as int[]
  }

  @Override
  Class returnedClass()
  {
    return Geometry
  }

  @Override
  boolean equals(Object x, Object y) throws HibernateException
  {
    if ( x == y )
    {
      return true
    }
    else if ( null == x || null == y )
    {
      return false
    }
    else
    {
      return x.equals( y )
    }
  }

  @Override
  int hashCode(Object o) throws HibernateException
  {
    return o?.hashCode()
  }

  @Override
  Object nullSafeGet(ResultSet resultSet, String[] names, SessionImplementor sessionImplementor, Object o) throws HibernateException, SQLException
  {
    def s = resultSet.getObject( names[0] )

    if ( s != null )
    {
//      return GeoScript.unwrap( reader.read( s ) )
//      return reader.read( s )
      return GeoScript.wrap( s )
    }

    return null;
  }

  @Override
  void nullSafeSet(PreparedStatement preparedStatement, Object o, int i, SessionImplementor sessionImplementor) throws HibernateException, SQLException
  {
    if ( null == o )
    {
      preparedStatement.setNull( i, sqlTypes()[0] )
    }
    else
    {
      //preparedStatement.setString( i, GeoScript.wrap( o ).wkt )
      //preparedStatement.setString( i, writer.write( o ) )
      preparedStatement.setString( i, o.wkt )
    }
  }

  @Override
  Object deepCopy(Object o) throws HibernateException
  {
    return o
  }

  @Override
  boolean isMutable()
  {
    return false
  }

  @Override
  Serializable disassemble(Object o) throws HibernateException
  {
    return (Serializable)o
  }

  @Override
  Object assemble(Serializable serializable, Object o) throws HibernateException
  {
    return serializable
  }

  @Override
  Object replace(Object o, Object o1, Object o2) throws HibernateException
  {
    return o
  }
}
