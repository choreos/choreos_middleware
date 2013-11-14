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

ruby_block "disable-service9159e3c2-45ff-42fa-bed5-988087f3830c" do
  block do
  	# do nothing!
  	i = 0
  end
  only_if { node.run_list.include?("recipe[service9159e3c2-45ff-42fa-bed5-988087f3830c::jar]") }
  notifies :delete, "file[#{node['CHOReOSData']['serviceData']['9159e3c2-45ff-42fa-bed5-988087f3830c']['InstallationDir']}/service-9159e3c2-45ff-42fa-bed5-988087f3830c.jar]", :immediately
end

ruby_block "remove-service9159e3c2-45ff-42fa-bed5-988087f3830c" do
  block do
  	node.run_list.remove("recipe[service9159e3c2-45ff-42fa-bed5-988087f3830c::jar]")
  end
  only_if { node.run_list.include?("recipe[service9159e3c2-45ff-42fa-bed5-988087f3830c::jar]") }
end

ruby_block "remove-deactivate-service9159e3c2-45ff-42fa-bed5-988087f3830c" do
  block do
    node.run_list.remove("recipe[deactivate-service9159e3c2-45ff-42fa-bed5-988087f3830c]")
  end
  only_if { node.run_list.include?("recipe[deactivate-service9159e3c2-45ff-42fa-bed5-988087f3830c]") }
end
