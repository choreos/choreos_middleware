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

#ruby_block "disable-servicea0031fb0-f30a-4b1f-89fa-4e87de079c64" do
#  block do
#  	# do nothing!
#  	i = 0
#  end
#  only_if { node.run_list.include?("recipe[servicea0031fb0-f30a-4b1f-89fa-4e87de079c64::jar]") }
#  notifies :delete, "file[#{node['CHOReOSData']['serviceData']['a0031fb0-f30a-4b1f-89fa-4e87de079c64']['InstallationDir']}/service-a0031fb0-f30a-4b1f-89fa-4e87de079c64.jar]", :immediately
#end

ruby_block "remove-servicea0031fb0-f30a-4b1f-89fa-4e87de079c64" do
  block do
  	node.run_list.remove("recipe[servicea0031fb0-f30a-4b1f-89fa-4e87de079c64::jar]")
  end
  only_if { node.run_list.include?("recipe[servicea0031fb0-f30a-4b1f-89fa-4e87de079c64::jar]") }
end

ruby_block "remove-deactivate-servicea0031fb0-f30a-4b1f-89fa-4e87de079c64" do
  block do
    node.run_list.remove("recipe[deactivate-servicea0031fb0-f30a-4b1f-89fa-4e87de079c64]")
  end
  only_if { node.run_list.include?("recipe[deactivate-servicea0031fb0-f30a-4b1f-89fa-4e87de079c64]") }
end
