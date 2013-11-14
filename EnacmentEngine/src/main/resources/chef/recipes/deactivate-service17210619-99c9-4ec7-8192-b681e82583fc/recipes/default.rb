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

ruby_block "remove-service17210619-99c9-4ec7-8192-b681e82583fc" do
  block do
  	# do nothing!
  	i = 0
  end
  only_if { node.run_list.include?("recipe[service17210619-99c9-4ec7-8192-b681e82583fc::jar]") }
  notifies :delete, "file[#{node['tomcat']['webapp_dir']}/17210619-99c9-4ec7-8192-b681e82583fc.war]", :immediately
end

ruby_block "remove-service17210619-99c9-4ec7-8192-b681e82583fc" do
  block do
  	node.run_list.remove("recipe[service17210619-99c9-4ec7-8192-b681e82583fc::war]")
  end
  only_if { node.run_list.include?("recipe[service17210619-99c9-4ec7-8192-b681e82583fc::war]") }
end

ruby_block "remove-deactivate-service17210619-99c9-4ec7-8192-b681e82583fc" do
  block do
    node.run_list.remove("recipe[deactivate-service17210619-99c9-4ec7-8192-b681e82583fc]")
  end
  only_if { node.run_list.include?("recipe[deactivate-service17210619-99c9-4ec7-8192-b681e82583fc]") }
end
