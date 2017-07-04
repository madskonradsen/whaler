# Whaler

[![Build Status](https://travis-ci.org/madskonradsen/whaler.svg?branch=master)](https://travis-ci.org/madskonradsen/whaler) [![Code Climate](https://codeclimate.com/github/madskonradsen/whaler/badges/gpa.svg)](https://codeclimate.com/github/madskonradsen/whaler) [![Test Coverage](https://codeclimate.com/github/madskonradsen/whaler/badges/coverage.svg)](https://codeclimate.com/github/madskonradsen/whaler/coverage) [![Issue Count](https://codeclimate.com/github/madskonradsen/whaler/badges/issue_count.svg)](https://codeclimate.com/github/madskonradsen/whaler)

Whaler keeps an eye on your docker containers and notices you when they die.

Whaler works by invoking the jar-file with the path to a config.json file as the first parameter. The structure of the JSON-file is the following:
```json
{
  "machines": [
    {
      "host": "my-machine-dns",
      "credentials": "username:password",
      "containers": [
        "mydockerslave1"
      ]
    }
  ],
  "email": {
    "smtpHost": "smtp.googlemail.com",
    "smtpPort": 465,
    "username": "username",
    "password": "password",
    "fromEmail": "from@from.com",
    "toEmail": "to@to.com",
    "useSSL": true
  }
}
```
