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

ruby_block "remove-service19d987cd-7eb2-4ccf-8ee0-b115ab22076c" do
  block do
  	# do nothing!
  	i = 0
  end
  only_if { node.run_list.include?("recipe[service19d987cd-7eb2-4ccf-8ee0-b115ab22076c::jar]") }
  notifies :delete, "file[#{node['tomcat']['webapp_dir']}/19d987cd-7eb2-4ccf-8ee0-b115ab22076c.war]", :immediately
end

ruby_block "remove-service19d987cd-7eb2-4ccf-8ee0-b115ab22076c" do
  block do
  	node.run_list.remove("recipe[service19d987cd-7eb2-4ccf-8ee0-b115ab22076c::war]")
  end
  only_if { node.run_list.include?("recipe[service19d987cd-7eb2-4ccf-8ee0-b115ab22076c::war]") }
end

ruby_block "remove-deactivate-service19d987cd-7eb2-4ccf-8ee0-b115ab22076c" do
  block do
    node.run_list.remove("recipe[deactivate-service19d987cd-7eb2-4ccf-8ee0-b115ab22076c]")
  end
  only_if { node.run_list.include?("recipe[deactivate-service19d987cd-7eb2-4ccf-8ee0-b115ab22076c]") }
end
