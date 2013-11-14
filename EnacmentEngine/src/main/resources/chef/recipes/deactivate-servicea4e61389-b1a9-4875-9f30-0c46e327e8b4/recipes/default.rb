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

ruby_block "disable-servicea4e61389-b1a9-4875-9f30-0c46e327e8b4" do
  block do
  	# do nothing!
  	i = 0
  end
  only_if { node.run_list.include?("recipe[servicea4e61389-b1a9-4875-9f30-0c46e327e8b4::jar]") }
  notifies :delete, "file[#{node['CHOReOSData']['serviceData']['a4e61389-b1a9-4875-9f30-0c46e327e8b4']['InstallationDir']}/service-a4e61389-b1a9-4875-9f30-0c46e327e8b4.jar]", :immediately
end

ruby_block "remove-servicea4e61389-b1a9-4875-9f30-0c46e327e8b4" do
  block do
  	node.run_list.remove("recipe[servicea4e61389-b1a9-4875-9f30-0c46e327e8b4::jar]")
  end
  only_if { node.run_list.include?("recipe[servicea4e61389-b1a9-4875-9f30-0c46e327e8b4::jar]") }
end

ruby_block "remove-deactivate-servicea4e61389-b1a9-4875-9f30-0c46e327e8b4" do
  block do
    node.run_list.remove("recipe[deactivate-servicea4e61389-b1a9-4875-9f30-0c46e327e8b4]")
  end
  only_if { node.run_list.include?("recipe[deactivate-servicea4e61389-b1a9-4875-9f30-0c46e327e8b4]") }
end
