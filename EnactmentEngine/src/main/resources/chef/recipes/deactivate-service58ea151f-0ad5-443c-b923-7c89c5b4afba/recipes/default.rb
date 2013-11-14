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

ruby_block "disable-service58ea151f-0ad5-443c-b923-7c89c5b4afba" do
  block do
  	# do nothing!
  	i = 0
  end
  only_if { node.run_list.include?("recipe[service58ea151f-0ad5-443c-b923-7c89c5b4afba::jar]") }
  notifies :delete, "file[#{node['CHOReOSData']['serviceData']['58ea151f-0ad5-443c-b923-7c89c5b4afba']['InstallationDir']}/service-58ea151f-0ad5-443c-b923-7c89c5b4afba.jar]", :immediately
end

ruby_block "remove-service58ea151f-0ad5-443c-b923-7c89c5b4afba" do
  block do
  	node.run_list.remove("recipe[service58ea151f-0ad5-443c-b923-7c89c5b4afba::jar]")
  end
  only_if { node.run_list.include?("recipe[service58ea151f-0ad5-443c-b923-7c89c5b4afba::jar]") }
end

ruby_block "remove-deactivate-service58ea151f-0ad5-443c-b923-7c89c5b4afba" do
  block do
    node.run_list.remove("recipe[deactivate-service58ea151f-0ad5-443c-b923-7c89c5b4afba]")
  end
  only_if { node.run_list.include?("recipe[deactivate-service58ea151f-0ad5-443c-b923-7c89c5b4afba]") }
end
