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

ruby_block "disable-service92116695-8621-4999-b270-48cc465e58c0" do
  block do
  	# do nothing!
  	i = 0
  end
  only_if { node.run_list.include?("recipe[service92116695-8621-4999-b270-48cc465e58c0::jar]") }
  notifies :delete, "file[#{node['CHOReOSData']['serviceData']['92116695-8621-4999-b270-48cc465e58c0']['InstallationDir']}/service-92116695-8621-4999-b270-48cc465e58c0.jar]", :immediately
end

ruby_block "remove-service92116695-8621-4999-b270-48cc465e58c0" do
  block do
  	node.run_list.remove("recipe[service92116695-8621-4999-b270-48cc465e58c0::jar]")
  end
  only_if { node.run_list.include?("recipe[service92116695-8621-4999-b270-48cc465e58c0::jar]") }
end

ruby_block "remove-deactivate-service92116695-8621-4999-b270-48cc465e58c0" do
  block do
    node.run_list.remove("recipe[deactivate-service92116695-8621-4999-b270-48cc465e58c0]")
  end
  only_if { node.run_list.include?("recipe[deactivate-service92116695-8621-4999-b270-48cc465e58c0]") }
end
