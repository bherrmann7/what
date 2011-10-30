// locations to search for config files that get merged into the main config
// config files can either be Java properties files or ConfigSlurper scripts

// griffon.config.locations = [ "classpath:${appName}-config.properties",
//                             "classpath:${appName}-config.groovy",
//                             "file:${userHome}/.griffon/${appName}-config.properties",
//                             "file:${userHome}/.griffon/${appName}-config.groovy"]

// if(System.properties["${appName}.config.location"]) {
//    griffon.config.locations << "file:" + System.properties["${appName}.config.location"]
// }


// log4j configuration
log4j {
    appender.stdout = "org.apache.log4j.ConsoleAppender"
    appender.'stdout.layout'="org.apache.log4j.PatternLayout"
    appender.'stdout.layout.ConversionPattern'='[%r] %c{2} %m%n'
    appender.errors = "org.apache.log4j.FileAppender"
    appender.'errors.layout'="org.apache.log4j.PatternLayout"
    appender.'errors.layout.ConversionPattern'='[%r] %c{2} %m%n'
    appender.'errors.File'="stacktrace.log"
    rootLogger="error,stdout"
    logger {
        griffon="error"
        StackTrace="error,errors"
        org {
            codehaus.groovy.griffon.commons="info" // core / classloading
        }
    }
    additivity.StackTrace=false
}

// key signing information
environments {
    development {
        signingkey {
            params {
                keystore = "${basedir}/griffon-app/conf/keys/devKeystore"
                alias = 'development'
                storepass = 'BadStorePassword'
                keypass   = 'BadKeyPassword'
                lazy      = true // only sign when unsigned
            }
        }

    }
    production {
        signingkey {
            params {
                keystore = "CHANGE ME"
                alias = 'CHAMGE ME'
                // NOTE: for production keys it is more secure to rely on key prompting
                // no value means we will prompt //storepass = 'BadStorePassword'
                // no value means we will prompt //keypass   = 'BadKeyPassword'
                lazy = false // sign, regardless of existing signatures
            }
        }

        griffon {
            jars {
                destDir = "${basedir}/target"
            }
            webstart {
                codebase = "CHANGE ME"
            }
        }
    }
}

griffon {
    jars {
        destDir = "${basedir}/target"
        jarName = "${appName}.jar"
    }
    webstart {
        codebase = "${new File(griffon.jars.destDir).toURI().toASCIIString()}"
        jnlp = "application.jnlp"
    }
    applet {
        jnlp = "applet.jnlp"
        html = "applet.html"
    }
}


// The following properties have been added by the Upgrade process...
griffon.jars.pack=false // jars were not automatically packed in Griffon 0.0
griffon.jars.sign=true // jars were automatically signed in Griffon 0.0
griffon.extensions.jarUrls = [] // remote jars were not possible in Griffon 0.1
griffon.extensions.jnlpUrls = [] // remote jars were not possible in Griffon 0.1
signingkey.params.sigfile='GRIFFON' // may safely be removed, but calling upgrade will restore it
// you may now tweak memory parameters
//griffon.memory.min='16m'
//griffon.memory.max='64m'
//griffon.memory.maxPermSize='64m'
deploy {
    application {
        title = '@griffonAppName@ @griffonAppVersion@'
        vendor = System.properties['user.name']
        homepage = 'http://localhost/@griffonAppName@'
        description {
            complete = '@griffonAppName@ @griffonAppVersion@'
            oneline  = '@griffonAppName@ @griffonAppVersion@'
            minimal  = '@griffonAppName@ @griffonAppVersion@'
            tooltip  = '@griffonAppName@ @griffonAppVersion@'
        }
        icon {
            fallback {
                name = 'griffon-icon-48x48.png'
                width = '48'
                height = '48'
            }
            splash {
                name = 'griffon.png'
                width = '391'
                height = '123'
            }
            menu {
                name = 'griffon-icon-16x16.png'
                width = '48'
                height = '48'
            }
            desktop {
                name = 'griffon-icon-32x32.png'
                width = '32'
                height = '32'
            }
        }
    }
}
griffon.project.dependency.resolution = {
    // inherit Griffon' default dependencies
    inherits("global") {
    }
    log "warn" // log level of Ivy resolver, either 'error', 'warn', 'info', 'debug' or 'verbose'
    repositories {
        griffonPlugins()
        griffonHome()
        griffonCentral()

        // uncomment the below to enable remote dependency resolution
        // from public Maven repositories
        //mavenLocal()
        //mavenCentral()
        //mavenRepo "http://snapshots.repository.codehaus.org"
        //mavenRepo "http://repository.codehaus.org"
        //mavenRepo "http://download.java.net/maven/2/"
        //mavenRepo "http://repository.jboss.com/maven2/"
    }
    dependencies {
        // specify dependencies here under either 'build', 'compile', 'runtime', 'test' or 'provided' scopes eg.

        // runtime 'mysql:mysql-connector-java:5.1.5'
    }
}

log4j = {
    // Example of changing the log pattern for the default console
    // appender:
    appenders {
        console name: 'stdout', layout: pattern(conversionPattern: '%d [%t] %-5p %c - %m%n')
    }

    error  'org.codehaus.griffon'
}

griffon {
    doc {
        logo = '<a href="http://griffon.codehaus.org" target="_blank"><img alt="The Griffon Framework" src="../img/griffon.png" border="0"/></a>'
        sponsorLogo = "<br/>"
        footer = "<br/><br/>Made with Griffon (0.9.4)"
    }
}

deploy {
    application {
        title = '@griffonAppName@ @griffonAppVersion@'
        vendor = System.properties['user.name']
        homepage = 'http://localhost/@griffonAppName@'
        description {
            complete = '@griffonAppName@ @griffonAppVersion@'
            oneline  = '@griffonAppName@ @griffonAppVersion@'
            minimal  = '@griffonAppName@ @griffonAppVersion@'
            tooltip  = '@griffonAppName@ @griffonAppVersion@'
        }
        icon {
            fallback {
                name = 'griffon-icon-48x48.png'
                width = '48'
                height = '48'
            }
            splash {
                name = 'griffon.png'
                width = '391'
                height = '123'
            }
            menu {
                name = 'griffon-icon-16x16.png'
                width = '48'
                height = '48'
            }
            desktop {
                name = 'griffon-icon-32x32.png'
                width = '32'
                height = '32'
            }
        }
    }
}