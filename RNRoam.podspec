
require 'json'

package = JSON.parse(File.read(File.join(__dir__, 'package.json')))

Pod::Spec.new do |s|
  s.name           = 'RNRoam'
  s.version        = package['version']
  s.summary        = package['description']
  s.description    = package['description']
  s.license        = package['license']
  s.author         = package['author']
  s.homepage       = package['homepage']
  s.source         = { :git => "#{package["repository"]["url"]}.git", :tag => "#{s.version}" }

  s.requires_arc   = true
  s.platform       = :ios, '10.0'

  s.preserve_paths = 'LICENSE', 'README.md', 'package.json', 'index.js'
  s.source_files   = './*.{h,m}'

  s.dependency 'React'
  #  s.dependency 'roam-ios/Roam', '0.1.30-beta.9'
   s.dependency 'roam-ios/RoamBatchConnector', '0.1.33'
   s.dependency 'roam-ios/RoamGeofence', '0.1.33'
end
