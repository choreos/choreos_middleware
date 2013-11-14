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

ruby_block "remove-service8de8c865-f8b2-4180-87f9-f85538e345fe" do
  block do
  	# do nothing!
  	i = 0
  end
  only_if { node.run_list.include?("recipe[service8de8c865-f8b2-4180-87f9-f85538e345fe::jar]") }
  notifies :delete, "file[#{node['tomcat']['webapp_dir']}/8de8c865-f8b2-4180-87f9-f85538e345fe.war]", :immediately
end

ruby_block "remove-service8de8c865-f8b2-4180-87f9-f85538e345fe" do
  block do
  	node.run_list.remove("recipe[service8de8c865-f8b2-4180-87f9-f85538e345fe::war]")
  end
  only_if { node.run_list.include?("recipe[service8de8c865-f8b2-4180-87f9-f85538e345fe::war]") }
end

ruby_block "remove-deactivate-service8de8c865-f8b2-4180-87f9-f85538e345fe" do
  block do
    node.run_list.remove("recipe[deactivate-service8de8c865-f8b2-4180-87f9-f85538e345fe]")
  end
  only_if { node.run_list.include?("recipe[deactivate-service8de8c865-f8b2-4180-87f9-f85538e345fe]") }
end
