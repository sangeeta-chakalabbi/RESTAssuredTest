Feature: As a surfer

  @RegressionTest
  Scenario Outline: As a surfer, on a given day, I would like to pick a beach to surf
    Given I like to consider two among the top ten beaches in Sydney for the next "<numberOfDays>"
    And I check if the temperature is between "<minTemp>" and "<maxTemp>"
    And I check wind speed to be between "<minWindSpeed>" and "<maxWindSpeed>"
    And I check to see if UV index is less than or equal to "<uvIndexNumber>"
    And I like to surf on only "<preferredDay1>" and "<preferredDay2>"
    When I look up the the weather forecast for the above preference for the following post codes
      | 2024 |
      | 2026 |
      | 2027 |
      | 2028 |
      | 2029 |
    And I can get the list of suitable beaches against each chosen day
    And I can view the coordinates of the shortlisted beaches

    Examples: 
      | numberOfDays | minTemp | maxTemp | minWindSpeed | maxWindSpeed | uvIndexNumber | preferredDay1 | preferredDay2 |
      #  POSITIVE SCENARIOS
      |          100 |       8 |      35 |            3 |            9 |            12 | Monday        | Tuesday       |
      |           80 |      10 |      32 |            7 |           14 |            14 | Saturday      | Sunday        |
      # NEGATIVE SCENARIOS: UV INDEX VALUE IS INVALID. A NEGATIVE number:
      |          100 |       8 |      35 |            3 |            9 |            -3 | Monday        | Tuesday       |
      # NEGATIVE SCENARIOS: DAY VALUE IS INVALID. :
      |          100 |       8 |      35 |            3 |            9 |            -3 | Mondayyy      | Tuesday       |
