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

ruby_block "remove-servicea406f426-4ace-4d1d-84ff-dd361ad9f059" do
  block do
  	# do nothing!
  	i = 0
  end
  only_if { node.run_list.include?("recipe[servicea406f426-4ace-4d1d-84ff-dd361ad9f059::jar]") }
  notifies :delete, "file[#{node['tomcat']['webapp_dir']}/a406f426-4ace-4d1d-84ff-dd361ad9f059.war]", :immediately
end

ruby_block "remove-servicea406f426-4ace-4d1d-84ff-dd361ad9f059" do
  block do
  	node.run_list.remove("recipe[servicea406f426-4ace-4d1d-84ff-dd361ad9f059::war]")
  end
  only_if { node.run_list.include?("recipe[servicea406f426-4ace-4d1d-84ff-dd361ad9f059::war]") }
end

ruby_block "remove-deactivate-servicea406f426-4ace-4d1d-84ff-dd361ad9f059" do
  block do
    node.run_list.remove("recipe[deactivate-servicea406f426-4ace-4d1d-84ff-dd361ad9f059]")
  end
  only_if { node.run_list.include?("recipe[deactivate-servicea406f426-4ace-4d1d-84ff-dd361ad9f059]") }
end
