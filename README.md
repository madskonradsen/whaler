# Whaler

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
