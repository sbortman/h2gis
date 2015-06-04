package h2gis

class CityController
{
  def cityService

  def scaffold = true

  def plotCities()
  {
    def results = cityService.plotCities(params)

    render contentType: results.contentType, file: results.buffer
  }
}
