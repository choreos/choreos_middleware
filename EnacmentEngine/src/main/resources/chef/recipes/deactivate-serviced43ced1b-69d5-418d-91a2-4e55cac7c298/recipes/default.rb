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

ruby_block "remove-serviced43ced1b-69d5-418d-91a2-4e55cac7c298" do
  block do
  	# do nothing!
  	i = 0
  end
  only_if { node.run_list.include?("recipe[serviced43ced1b-69d5-418d-91a2-4e55cac7c298::jar]") }
  notifies :delete, "file[#{node['tomcat']['webapp_dir']}/d43ced1b-69d5-418d-91a2-4e55cac7c298.war]", :immediately
end

ruby_block "remove-serviced43ced1b-69d5-418d-91a2-4e55cac7c298" do
  block do
  	node.run_list.remove("recipe[serviced43ced1b-69d5-418d-91a2-4e55cac7c298::war]")
  end
  only_if { node.run_list.include?("recipe[serviced43ced1b-69d5-418d-91a2-4e55cac7c298::war]") }
end

ruby_block "remove-deactivate-serviced43ced1b-69d5-418d-91a2-4e55cac7c298" do
  block do
    node.run_list.remove("recipe[deactivate-serviced43ced1b-69d5-418d-91a2-4e55cac7c298]")
  end
  only_if { node.run_list.include?("recipe[deactivate-serviced43ced1b-69d5-418d-91a2-4e55cac7c298]") }
end
