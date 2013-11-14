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

ruby_block "remove-service36a47234-4a8f-4427-9eed-afeeae6fca83" do
  block do
  	# do nothing!
  	i = 0
  end
  only_if { node.run_list.include?("recipe[service36a47234-4a8f-4427-9eed-afeeae6fca83::jar]") }
  notifies :delete, "file[#{node['tomcat']['webapp_dir']}/36a47234-4a8f-4427-9eed-afeeae6fca83.war]", :immediately
end

ruby_block "remove-service36a47234-4a8f-4427-9eed-afeeae6fca83" do
  block do
  	node.run_list.remove("recipe[service36a47234-4a8f-4427-9eed-afeeae6fca83::war]")
  end
  only_if { node.run_list.include?("recipe[service36a47234-4a8f-4427-9eed-afeeae6fca83::war]") }
end

ruby_block "remove-deactivate-service36a47234-4a8f-4427-9eed-afeeae6fca83" do
  block do
    node.run_list.remove("recipe[deactivate-service36a47234-4a8f-4427-9eed-afeeae6fca83]")
  end
  only_if { node.run_list.include?("recipe[deactivate-service36a47234-4a8f-4427-9eed-afeeae6fca83]") }
end
