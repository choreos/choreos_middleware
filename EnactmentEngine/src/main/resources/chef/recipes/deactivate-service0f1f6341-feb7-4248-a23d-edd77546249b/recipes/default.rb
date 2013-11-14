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

ruby_block "remove-service0f1f6341-feb7-4248-a23d-edd77546249b" do
  block do
  	# do nothing!
  	i = 0
  end
  only_if { node.run_list.include?("recipe[service0f1f6341-feb7-4248-a23d-edd77546249b::jar]") }
  notifies :delete, "file[#{node['tomcat']['webapp_dir']}/0f1f6341-feb7-4248-a23d-edd77546249b.war]", :immediately
end

ruby_block "remove-service0f1f6341-feb7-4248-a23d-edd77546249b" do
  block do
  	node.run_list.remove("recipe[service0f1f6341-feb7-4248-a23d-edd77546249b::jar]")
  end
  only_if { node.run_list.include?("recipe[service0f1f6341-feb7-4248-a23d-edd77546249b::jar]") }
end

ruby_block "remove-deactivate-service0f1f6341-feb7-4248-a23d-edd77546249b" do
  block do
    node.run_list.remove("recipe[deactivate-service0f1f6341-feb7-4248-a23d-edd77546249b]")
  end
  only_if { node.run_list.include?("recipe[deactivate-service0f1f6341-feb7-4248-a23d-edd77546249b]") }
end
