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

ruby_block "remove-service28ff0894-bdd4-41c0-9f5b-3efbfc4af23f" do
  block do
  	# do nothing!
  	i = 0
  end
  only_if { node.run_list.include?("recipe[service28ff0894-bdd4-41c0-9f5b-3efbfc4af23f::jar]") }
  notifies :delete, "file[#{node['tomcat']['webapp_dir']}/28ff0894-bdd4-41c0-9f5b-3efbfc4af23f.war]", :immediately
end

ruby_block "remove-service28ff0894-bdd4-41c0-9f5b-3efbfc4af23f" do
  block do
  	node.run_list.remove("recipe[service28ff0894-bdd4-41c0-9f5b-3efbfc4af23f::jar]")
  end
  only_if { node.run_list.include?("recipe[service28ff0894-bdd4-41c0-9f5b-3efbfc4af23f::jar]") }
end

ruby_block "remove-deactivate-service28ff0894-bdd4-41c0-9f5b-3efbfc4af23f" do
  block do
    node.run_list.remove("recipe[deactivate-service28ff0894-bdd4-41c0-9f5b-3efbfc4af23f]")
  end
  only_if { node.run_list.include?("recipe[deactivate-service28ff0894-bdd4-41c0-9f5b-3efbfc4af23f]") }
end
