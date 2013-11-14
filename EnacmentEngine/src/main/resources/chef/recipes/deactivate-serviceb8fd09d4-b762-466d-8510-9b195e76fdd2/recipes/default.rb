#
# Cookbook Name:: generic-jar-service-template
# Recipe:: default
#
# Copyright 2012, YOUR_COMPANY_NAME
#
# All rights reserved - Do Not Redistribute
#

##########################################################################
#                  #
#                IMPORTANT DEVELOPMENT NOTICE:                           #
#                  #
# All ocurrences of $ NAME must be replaced with the actual service name #
#            before uploading the recipe to the chef-server              #
#                  #
##########################################################################

#ruby_block "disable-serviceb8fd09d4-b762-466d-8510-9b195e76fdd2" do
#  block do
#  	# do nothing!
#  	i = 0
#  end
#  only_if { node.run_list.include?("recipe[serviceb8fd09d4-b762-466d-8510-9b195e76fdd2::jar]") }
#  notifies :delete, "file[#{node['CHOReOSData']['serviceData']['b8fd09d4-b762-466d-8510-9b195e76fdd2']['InstallationDir']}/service-b8fd09d4-b762-466d-8510-9b195e76fdd2.jar]", :immediately
#end

ruby_block "remove-serviceb8fd09d4-b762-466d-8510-9b195e76fdd2" do
  block do
  	node.run_list.remove("recipe[serviceb8fd09d4-b762-466d-8510-9b195e76fdd2::jar]")
  end
  only_if { node.run_list.include?("recipe[serviceb8fd09d4-b762-466d-8510-9b195e76fdd2::jar]") }
end

ruby_block "remove-deactivate-serviceb8fd09d4-b762-466d-8510-9b195e76fdd2" do
  block do
    node.run_list.remove("recipe[deactivate-serviceb8fd09d4-b762-466d-8510-9b195e76fdd2]")
  end
  only_if { node.run_list.include?("recipe[deactivate-serviceb8fd09d4-b762-466d-8510-9b195e76fdd2]") }
end
