project {
    modelVersion '4.0.0'
    parent 'org.tautua:tautua:5'
    groupId 'org.tautua.iso8583'
    artifactId 'tautua.iso8583'
    version '${revision}'
    packaging 'bundle'
    inceptionYear '2024'

    developers {
        developer {
            name 'Larry Ruiz'
            email 'lruiz@tautua.org'
        }
    }

    properties {
        'revision' '1.0.0-SNAPSHOT'
        'java.version' '17'

        'bundle.import.service' ''
        'bundle.version' 'version=${project.version}'
        'bundle.failok' 'false'
        'bundle.symbolic.name' '${project.artifactId}'
        'bundle.export' '${bundle.export.package};-noimport:=true'
        'bundle.export.package' '!*.impl;!*.internal; *.${project.artifactId}.*'
        'bundle.export.version.policy' '$<range;[==,=+)>'
        'bundle.export.service' ''
        'bundle.split.package' '-split-package:=first'
        'bundle.import' '${bundle.import.package}'
        'bundle.import.package' '''
            ${bundle.import.before.defaults},
            ${bundle.import.defaults},
            ${bundle.import.additional},
            *'''
        'bundle.import.additional' ''
        'bundle.import.before.defaults' ''
        'bundle.import.defaults' ''
        'bundle.import.version.policy' '$<range;[==,+)>'
        'bundle.include.resource' '{maven-resources}'
        'bundle.dynamic' ''
        'bundle.private.package' '*.impl;*.internal;'
        'bundle.exclude.dependencies' 'true'
        'bundle.remove.headers' 'Ignore-Package,Include-Resource,Private-Package,Bundle-DocURL'
        'bundle.activator' ''
        'bundle.embed.dependency' ''
    }

    dependencyManagement {
        dependencies {
            dependency {
                groupId 'org.tautua.umayux'
                artifactId 'umayux.bom'
                version '1.0.0-SNAPSHOT'
                type 'pom'
                scope 'import'
            }
        }
    }

    dependencies {
        dependency 'org.tautua.foundation:tautua.foundation:1.0.0-SNAPSHOT';

        dependency ('org.slf4j:slf4j-api:1.7.36') {
            scope 'provided'
        }

        // osgi
        dependency('org.apache.felix:org.apache.felix.dependencymanager:4.6.1') {
            scope 'provided'
        }
        dependency('org.apache.felix:org.apache.felix.dependencymanager.lambda:1.2.2') {
            scope 'provided'
        }
        dependency('org.osgi:osgi.core:8.0.0') {
            scope 'provided'
        }

        // tests
        dependency('org.assertj:assertj-core') {
            scope 'test'
        }
        dependency('org.junit.jupiter:junit-jupiter-api') {
            scope 'test'
        }
        dependency('org.junit.jupiter:junit-jupiter-engine') {
            scope 'test'
        }
        dependency('org.junit.jupiter:junit-jupiter-params') {
            scope 'test'
        }
        dependency('org.mockito:mockito-core') {
            scope 'test'
        }
    }

    build {
        plugins {
            plugin('org.apache.felix:maven-bundle-plugin:5.1.9') {
                extensions 'true'
                inherited 'true'
                executions {
                    execution {
                        id 'src-metadata'
                        goals 'manifest'
                        configuration {
                            supportIncrementalBuild 'true'
                        }
                    }
                }
                configuration {
                    excludeDependencies '${bundle.exclude.dependencies}'
                    instructions {
                        'Bundle-Name' '${project.name}'
                        'Bundle-SymbolicName' '${bundle.symbolic.name}'
                        'Bundle-Vendor' 'Tautua'
                        'Bundle-Activator' '${bundle.activator}'
                        'Export-Package' '${bundle.export}'
                        'Import-Package' '${bundle.import}'
                        'Private-Package' '${bundle.private.package}'
                        'Implementation-Title' 'Tautua Umayux'
                        'Implementation-Version' '${project.version}'
                        'Include-Resource' '${bundle.include.resource}'
                        'Embed-Dependency' '${bundle.embed.dependency}'
                        '_removeheaders' '${bundle.remove.headers}'
                        '_failok' '${bundle.failok}'
                        '_consumer-policy' '${bundle.import.version.policy}'
                        '_provider-policy' '${bundle.export.version.policy}'
                        'Export-Service' '${bundle.export.service}'
                        'Import-Service' '${bundle.import.service}'
                        'Require-Capability' '${bundle.require.capability}'
                    }
                }
            }
        }
    }

    profiles {
        profile {
            id 'notest'
            properties {
                'maven.test.skip' 'true'
            }
        }
    }
}
