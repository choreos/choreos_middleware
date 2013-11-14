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

ruby_block "remove-service45ef1cbf-1b96-420a-8d8f-5fa7700a70e5" do
  block do
  	# do nothing!
  	i = 0
  end
  only_if { node.run_list.include?("recipe[service45ef1cbf-1b96-420a-8d8f-5fa7700a70e5::jar]") }
  notifies :delete, "file[#{node['tomcat']['webapp_dir']}/45ef1cbf-1b96-420a-8d8f-5fa7700a70e5.war]", :immediately
end

ruby_block "remove-service45ef1cbf-1b96-420a-8d8f-5fa7700a70e5" do
  block do
  	node.run_list.remove("recipe[service45ef1cbf-1b96-420a-8d8f-5fa7700a70e5::war]")
  end
  only_if { node.run_list.include?("recipe[service45ef1cbf-1b96-420a-8d8f-5fa7700a70e5::war]") }
end

ruby_block "remove-deactivate-service45ef1cbf-1b96-420a-8d8f-5fa7700a70e5" do
  block do
    node.run_list.remove("recipe[deactivate-service45ef1cbf-1b96-420a-8d8f-5fa7700a70e5]")
  end
  only_if { node.run_list.include?("recipe[deactivate-service45ef1cbf-1b96-420a-8d8f-5fa7700a70e5]") }
end
