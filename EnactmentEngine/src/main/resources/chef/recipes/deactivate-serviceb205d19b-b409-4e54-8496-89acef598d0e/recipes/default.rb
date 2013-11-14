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

ruby_block "remove-serviceb205d19b-b409-4e54-8496-89acef598d0e" do
  block do
  	# do nothing!
  	i = 0
  end
  only_if { node.run_list.include?("recipe[serviceb205d19b-b409-4e54-8496-89acef598d0e::jar]") }
  notifies :delete, "file[#{node['tomcat']['webapp_dir']}/b205d19b-b409-4e54-8496-89acef598d0e.war]", :immediately
end

ruby_block "remove-serviceb205d19b-b409-4e54-8496-89acef598d0e" do
  block do
  	node.run_list.remove("recipe[serviceb205d19b-b409-4e54-8496-89acef598d0e::war]")
  end
  only_if { node.run_list.include?("recipe[serviceb205d19b-b409-4e54-8496-89acef598d0e::war]") }
end

ruby_block "remove-deactivate-serviceb205d19b-b409-4e54-8496-89acef598d0e" do
  block do
    node.run_list.remove("recipe[deactivate-serviceb205d19b-b409-4e54-8496-89acef598d0e]")
  end
  only_if { node.run_list.include?("recipe[deactivate-serviceb205d19b-b409-4e54-8496-89acef598d0e]") }
end
