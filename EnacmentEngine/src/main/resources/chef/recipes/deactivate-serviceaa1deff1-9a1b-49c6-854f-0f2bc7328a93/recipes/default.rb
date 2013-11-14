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

ruby_block "remove-serviceaa1deff1-9a1b-49c6-854f-0f2bc7328a93" do
  block do
  	# do nothing!
  	i = 0
  end
  only_if { node.run_list.include?("recipe[serviceaa1deff1-9a1b-49c6-854f-0f2bc7328a93::jar]") }
  notifies :delete, "file[#{node['tomcat']['webapp_dir']}/aa1deff1-9a1b-49c6-854f-0f2bc7328a93.war]", :immediately
end

ruby_block "remove-serviceaa1deff1-9a1b-49c6-854f-0f2bc7328a93" do
  block do
  	node.run_list.remove("recipe[serviceaa1deff1-9a1b-49c6-854f-0f2bc7328a93::war]")
  end
  only_if { node.run_list.include?("recipe[serviceaa1deff1-9a1b-49c6-854f-0f2bc7328a93::war]") }
end

ruby_block "remove-deactivate-serviceaa1deff1-9a1b-49c6-854f-0f2bc7328a93" do
  block do
    node.run_list.remove("recipe[deactivate-serviceaa1deff1-9a1b-49c6-854f-0f2bc7328a93]")
  end
  only_if { node.run_list.include?("recipe[deactivate-serviceaa1deff1-9a1b-49c6-854f-0f2bc7328a93]") }
end
