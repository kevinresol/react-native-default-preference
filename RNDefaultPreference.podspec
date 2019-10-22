require 'json'

package = JSON.parse(File.read(File.join(__dir__, 'package.json')))

Pod::Spec.new do |s|
  s.name           = "RNDefaultPreference"
  s.version        = package['version']
  s.summary        = package['description']
  s.description    = package['description']
  s.license        = package['license']
  s.author         = package['author']
  s.homepage       = package['homepage']

  s.platforms      = { :ios => "9.0", :tvos => "11.0" }
  s.ios.deployment_target = '9.0'
  
  s.preserve_paths = 'README.md', 'package.json', 'index.js'
  s.source_files   = 'iOS/*.{h,m}'
  s.source         = { :git => 'https://github.com/kevinresol/react-native-default-preference.git' }

  s.dependency 'React'
end
