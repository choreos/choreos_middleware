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

#ruby_block "disable-servicedd2e66c6-1987-4918-9c2c-f0de11107c3d" do
#  block do
#  	# do nothing!
#  	i = 0
#  end
#  only_if { node.run_list.include?("recipe[servicedd2e66c6-1987-4918-9c2c-f0de11107c3d::jar]") }
#  notifies :delete, "file[#{node['CHOReOSData']['serviceData']['dd2e66c6-1987-4918-9c2c-f0de11107c3d']['InstallationDir']}/service-dd2e66c6-1987-4918-9c2c-f0de11107c3d.jar]", :immediately
#end

ruby_block "remove-servicedd2e66c6-1987-4918-9c2c-f0de11107c3d" do
  block do
  	node.run_list.remove("recipe[servicedd2e66c6-1987-4918-9c2c-f0de11107c3d::jar]")
  end
  only_if { node.run_list.include?("recipe[servicedd2e66c6-1987-4918-9c2c-f0de11107c3d::jar]") }
end

ruby_block "remove-deactivate-servicedd2e66c6-1987-4918-9c2c-f0de11107c3d" do
  block do
    node.run_list.remove("recipe[deactivate-servicedd2e66c6-1987-4918-9c2c-f0de11107c3d]")
  end
  only_if { node.run_list.include?("recipe[deactivate-servicedd2e66c6-1987-4918-9c2c-f0de11107c3d]") }
end
