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

ruby_block "remove-service4af74045-2e9a-49e8-8c66-f949cae707e8" do
  block do
  	# do nothing!
  	i = 0
  end
  only_if { node.run_list.include?("recipe[service4af74045-2e9a-49e8-8c66-f949cae707e8::jar]") }
  notifies :delete, "file[#{node['tomcat']['webapp_dir']}/4af74045-2e9a-49e8-8c66-f949cae707e8.war]", :immediately
end

ruby_block "remove-service4af74045-2e9a-49e8-8c66-f949cae707e8" do
  block do
  	node.run_list.remove("recipe[service4af74045-2e9a-49e8-8c66-f949cae707e8::war]")
  end
  only_if { node.run_list.include?("recipe[service4af74045-2e9a-49e8-8c66-f949cae707e8::war]") }
end

ruby_block "remove-deactivate-service4af74045-2e9a-49e8-8c66-f949cae707e8" do
  block do
    node.run_list.remove("recipe[deactivate-service4af74045-2e9a-49e8-8c66-f949cae707e8]")
  end
  only_if { node.run_list.include?("recipe[deactivate-service4af74045-2e9a-49e8-8c66-f949cae707e8]") }
end
