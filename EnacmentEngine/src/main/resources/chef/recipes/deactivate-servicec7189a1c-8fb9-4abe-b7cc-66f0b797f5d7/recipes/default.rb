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

ruby_block "remove-servicec7189a1c-8fb9-4abe-b7cc-66f0b797f5d7" do
  block do
  	# do nothing!
  	i = 0
  end
  only_if { node.run_list.include?("recipe[servicec7189a1c-8fb9-4abe-b7cc-66f0b797f5d7::jar]") }
  notifies :delete, "file[#{node['tomcat']['webapp_dir']}/c7189a1c-8fb9-4abe-b7cc-66f0b797f5d7.war]", :immediately
end

ruby_block "remove-servicec7189a1c-8fb9-4abe-b7cc-66f0b797f5d7" do
  block do
  	node.run_list.remove("recipe[servicec7189a1c-8fb9-4abe-b7cc-66f0b797f5d7::war]")
  end
  only_if { node.run_list.include?("recipe[servicec7189a1c-8fb9-4abe-b7cc-66f0b797f5d7::war]") }
end

ruby_block "remove-deactivate-servicec7189a1c-8fb9-4abe-b7cc-66f0b797f5d7" do
  block do
    node.run_list.remove("recipe[deactivate-servicec7189a1c-8fb9-4abe-b7cc-66f0b797f5d7]")
  end
  only_if { node.run_list.include?("recipe[deactivate-servicec7189a1c-8fb9-4abe-b7cc-66f0b797f5d7]") }
end
