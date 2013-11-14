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

ruby_block "remove-servicec9f9e71e-1589-4638-ad98-6df6f8ea8e94" do
  block do
  	# do nothing!
  	i = 0
  end
  only_if { node.run_list.include?("recipe[servicec9f9e71e-1589-4638-ad98-6df6f8ea8e94::jar]") }
  notifies :delete, "file[#{node['tomcat']['webapp_dir']}/c9f9e71e-1589-4638-ad98-6df6f8ea8e94.war]", :immediately
end

ruby_block "remove-servicec9f9e71e-1589-4638-ad98-6df6f8ea8e94" do
  block do
  	node.run_list.remove("recipe[servicec9f9e71e-1589-4638-ad98-6df6f8ea8e94::war]")
  end
  only_if { node.run_list.include?("recipe[servicec9f9e71e-1589-4638-ad98-6df6f8ea8e94::war]") }
end

ruby_block "remove-deactivate-servicec9f9e71e-1589-4638-ad98-6df6f8ea8e94" do
  block do
    node.run_list.remove("recipe[deactivate-servicec9f9e71e-1589-4638-ad98-6df6f8ea8e94]")
  end
  only_if { node.run_list.include?("recipe[deactivate-servicec9f9e71e-1589-4638-ad98-6df6f8ea8e94]") }
end
