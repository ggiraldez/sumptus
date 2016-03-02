# Sumptus

A simple expense tracker written in Clojure.

## Development

The application uses a Postgres database, named `sumptus`. Check the
configuration in the root `project.clj`. Assuming you have Postgres already
installed on your system, create the database by running:

    $ createdb sumptus

Then start the REPL through Leiningen with:

    $ lein repl

And start the server from the REPL:

    sumptus.web=> (sumptus.web/-main)

This will initialize the application, run the database migrations and start the
web server, by default on port 5000.

## Deployment to Heroku

Use the Heroku toolbelt command line tool to deploy Sumptus to Heroku.

    $ heroku create APP_NAME

The application uses a Postgres database, so you need to install the addon.

    $ heroku addons:create heroku-postgresql:hobby-dev

It's also convenient to set the Logback configuration to be a bit less verbose.
The default will print timestamps, which `heroku logs` already provide. A
special `logback-heroku.xml` file is provided in the resources directory. Simply
set the configuration key `JVM_OPTS` with the system variable to point to the
alternative file, like so:

    $ heroku config:set JVM_OPTS=-Dlogback.configurationFile=logback-heroku.xml

Then simply deploy by issuing a git push:

    $ git push heroku master

## License

Copyright © 2016 Gustavo Giráldez

Distributed under the Eclipse Public License either version 1.0 or (at
your option) any later version.
