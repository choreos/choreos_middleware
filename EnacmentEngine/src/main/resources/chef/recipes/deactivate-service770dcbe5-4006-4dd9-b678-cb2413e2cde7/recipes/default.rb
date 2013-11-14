#
# Cookbook Name:: generic-jar-service-template
# Recipe:: default
#
# Copyright 2012, YOUR_COMPANY_NAME
#
# All rights reserved - Do Not Redistribute
#

##########################################################################
#                  														 #
#                IMPORTANT DEVELOPMENT NOTICE:                           #
#                  														 #
# All ocurrences of $ NAME must be replaced with the actual service name #
#            before uploading the recipe to the chef-server              #
#                  														 #
##########################################################################

ruby_block "remove-service770dcbe5-4006-4dd9-b678-cb2413e2cde7" do
  block do
  	# do nothing!
  	i = 0
  end
  only_if { node.run_list.include?("recipe[service770dcbe5-4006-4dd9-b678-cb2413e2cde7::jar]") }
  notifies :delete, "file[#{node['tomcat']['webapp_dir']}/770dcbe5-4006-4dd9-b678-cb2413e2cde7.war]", :immediately
end

ruby_block "remove-service770dcbe5-4006-4dd9-b678-cb2413e2cde7" do
  block do
  	node.run_list.remove("recipe[service770dcbe5-4006-4dd9-b678-cb2413e2cde7::war]")
  end
  only_if { node.run_list.include?("recipe[service770dcbe5-4006-4dd9-b678-cb2413e2cde7::war]") }
end

ruby_block "remove-deactivate-service770dcbe5-4006-4dd9-b678-cb2413e2cde7" do
  block do
    node.run_list.remove("recipe[deactivate-service770dcbe5-4006-4dd9-b678-cb2413e2cde7]")
  end
  only_if { node.run_list.include?("recipe[deactivate-service770dcbe5-4006-4dd9-b678-cb2413e2cde7]") }
end
