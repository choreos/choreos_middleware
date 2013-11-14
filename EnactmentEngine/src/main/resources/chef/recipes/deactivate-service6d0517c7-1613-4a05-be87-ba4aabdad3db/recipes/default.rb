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

ruby_block "disable-service6d0517c7-1613-4a05-be87-ba4aabdad3db" do
  block do
  	# do nothing!
  	i = 0
  end
  only_if { node.run_list.include?("recipe[service6d0517c7-1613-4a05-be87-ba4aabdad3db::jar]") }
  notifies :delete, "file[#{node['CHOReOSData']['serviceData']['6d0517c7-1613-4a05-be87-ba4aabdad3db']['InstallationDir']}/service-6d0517c7-1613-4a05-be87-ba4aabdad3db.jar]", :immediately
end

ruby_block "remove-service6d0517c7-1613-4a05-be87-ba4aabdad3db" do
  block do
  	node.run_list.remove("recipe[service6d0517c7-1613-4a05-be87-ba4aabdad3db::jar]")
  end
  only_if { node.run_list.include?("recipe[service6d0517c7-1613-4a05-be87-ba4aabdad3db::jar]") }
end

ruby_block "remove-deactivate-service6d0517c7-1613-4a05-be87-ba4aabdad3db" do
  block do
    node.run_list.remove("recipe[deactivate-service6d0517c7-1613-4a05-be87-ba4aabdad3db]")
  end
  only_if { node.run_list.include?("recipe[deactivate-service6d0517c7-1613-4a05-be87-ba4aabdad3db]") }
end
