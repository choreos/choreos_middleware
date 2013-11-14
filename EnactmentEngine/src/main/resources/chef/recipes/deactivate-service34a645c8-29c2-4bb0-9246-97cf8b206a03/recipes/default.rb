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

ruby_block "disable-service34a645c8-29c2-4bb0-9246-97cf8b206a03" do
  block do
  	# do nothing!
  	i = 0
  end
  only_if { node.run_list.include?("recipe[service34a645c8-29c2-4bb0-9246-97cf8b206a03::jar]") }
  notifies :delete, "file[#{node['CHOReOSData']['serviceData']['34a645c8-29c2-4bb0-9246-97cf8b206a03']['InstallationDir']}/service-34a645c8-29c2-4bb0-9246-97cf8b206a03.jar]", :immediately
end

ruby_block "remove-service34a645c8-29c2-4bb0-9246-97cf8b206a03" do
  block do
  	node.run_list.remove("recipe[service34a645c8-29c2-4bb0-9246-97cf8b206a03::jar]")
  end
  only_if { node.run_list.include?("recipe[service34a645c8-29c2-4bb0-9246-97cf8b206a03::jar]") }
end

ruby_block "remove-deactivate-service34a645c8-29c2-4bb0-9246-97cf8b206a03" do
  block do
    node.run_list.remove("recipe[deactivate-service34a645c8-29c2-4bb0-9246-97cf8b206a03]")
  end
  only_if { node.run_list.include?("recipe[deactivate-service34a645c8-29c2-4bb0-9246-97cf8b206a03]") }
end
