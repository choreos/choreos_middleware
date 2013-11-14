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

ruby_block "remove-serviced9342d0f-8f87-46a2-b095-99e7a4790986" do
  block do
  	# do nothing!
  	i = 0
  end
  only_if { node.run_list.include?("recipe[serviced9342d0f-8f87-46a2-b095-99e7a4790986::jar]") }
  notifies :delete, "file[#{node['tomcat']['webapp_dir']}/d9342d0f-8f87-46a2-b095-99e7a4790986.war]", :immediately
end

ruby_block "remove-serviced9342d0f-8f87-46a2-b095-99e7a4790986" do
  block do
  	node.run_list.remove("recipe[serviced9342d0f-8f87-46a2-b095-99e7a4790986::war]")
  end
  only_if { node.run_list.include?("recipe[serviced9342d0f-8f87-46a2-b095-99e7a4790986::war]") }
end

ruby_block "remove-deactivate-serviced9342d0f-8f87-46a2-b095-99e7a4790986" do
  block do
    node.run_list.remove("recipe[deactivate-serviced9342d0f-8f87-46a2-b095-99e7a4790986]")
  end
  only_if { node.run_list.include?("recipe[deactivate-serviced9342d0f-8f87-46a2-b095-99e7a4790986]") }
end
