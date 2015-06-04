/**
 * Created by sbortman on 6/3/15.
 */

//= require webjars/openlayers/3.5.0/ol.js
//= require_self

var MapWidget = (function ()
{
    return {
        init: function ()
        {
            var layers = [
                new ol.layer.Tile( {
                    source: new ol.source.TileWMS( {
                        url: 'http://demo.boundlessgeo.com/geoserver/wms',
                        params: {
                            'LAYERS': 'ne:NE1_HR_LC_SR_W_DR'
                        }
                    } )
                } ),
                new ol.layer.Tile( {
                    source: new ol.source.TileWMS( {
                        url: '/h2gis/city/plotCities',
                        params: {LAYERS: 'city', VERSION: '1.1.1'}
                    } )
                } )
            ];

            var map = new ol.Map( {
                controls: ol.control.defaults().extend( [
                    new ol.control.ScaleLine( {
                        units: 'degrees'
                    } )
                ] ),
                layers: layers,
                target: 'map',
                view: new ol.View( {
                    projection: 'EPSG:4326',
                    center: [0, 0],
                    zoom: 2
                } )
            } );
        }
    }
})();
