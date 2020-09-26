## Check deps and plugins updates

In order to enable sbt-updates check plugins updates we must add the addSbt(...) 
command on the [global plugin file](https://github.com/rtimush/sbt-updates/issues/98)
An alias **checkdeps** was added to make this work (check plugins + libraries)