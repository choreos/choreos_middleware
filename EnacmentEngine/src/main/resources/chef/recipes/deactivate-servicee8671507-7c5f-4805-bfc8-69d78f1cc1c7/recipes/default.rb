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

ruby_block "remove-servicee8671507-7c5f-4805-bfc8-69d78f1cc1c7" do
  block do
  	# do nothing!
  	i = 0
  end
  only_if { node.run_list.include?("recipe[servicee8671507-7c5f-4805-bfc8-69d78f1cc1c7::jar]") }
  notifies :delete, "file[#{node['tomcat']['webapp_dir']}/e8671507-7c5f-4805-bfc8-69d78f1cc1c7.war]", :immediately
end

ruby_block "remove-servicee8671507-7c5f-4805-bfc8-69d78f1cc1c7" do
  block do
  	node.run_list.remove("recipe[servicee8671507-7c5f-4805-bfc8-69d78f1cc1c7::war]")
  end
  only_if { node.run_list.include?("recipe[servicee8671507-7c5f-4805-bfc8-69d78f1cc1c7::war]") }
end

ruby_block "remove-deactivate-servicee8671507-7c5f-4805-bfc8-69d78f1cc1c7" do
  block do
    node.run_list.remove("recipe[deactivate-servicee8671507-7c5f-4805-bfc8-69d78f1cc1c7]")
  end
  only_if { node.run_list.include?("recipe[deactivate-servicee8671507-7c5f-4805-bfc8-69d78f1cc1c7]") }
end
