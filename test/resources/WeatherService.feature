Feature: 

        As weather web service user

        I will be able to get the weather information by the predefined operations in the WSDL

        

@getWeatherByZipcode

    Scenario: Get the city weather information by zipcode

             Given I have a list of zipcode 

             When I use the weather service to get the weather information

             Then the information should be found successfully