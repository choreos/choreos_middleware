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

ruby_block "disable-servicea64bf426-c2d0-4863-b30e-1f005df793a6" do
  block do
  	# do nothing!
  	i = 0
  end
  only_if { node.run_list.include?("recipe[servicea64bf426-c2d0-4863-b30e-1f005df793a6::jar]") }
  notifies :delete, "file[#{node['CHOReOSData']['serviceData']['a64bf426-c2d0-4863-b30e-1f005df793a6']['InstallationDir']}/service-a64bf426-c2d0-4863-b30e-1f005df793a6.jar]", :immediately
end

ruby_block "remove-servicea64bf426-c2d0-4863-b30e-1f005df793a6" do
  block do
  	node.run_list.remove("recipe[servicea64bf426-c2d0-4863-b30e-1f005df793a6::jar]")
  end
  only_if { node.run_list.include?("recipe[servicea64bf426-c2d0-4863-b30e-1f005df793a6::jar]") }
end

ruby_block "remove-deactivate-servicea64bf426-c2d0-4863-b30e-1f005df793a6" do
  block do
    node.run_list.remove("recipe[deactivate-servicea64bf426-c2d0-4863-b30e-1f005df793a6]")
  end
  only_if { node.run_list.include?("recipe[deactivate-servicea64bf426-c2d0-4863-b30e-1f005df793a6]") }
end
