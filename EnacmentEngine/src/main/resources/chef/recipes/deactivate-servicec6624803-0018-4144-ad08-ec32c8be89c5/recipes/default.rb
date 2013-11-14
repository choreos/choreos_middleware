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

ruby_block "remove-servicec6624803-0018-4144-ad08-ec32c8be89c5" do
  block do
  	# do nothing!
  	i = 0
  end
  only_if { node.run_list.include?("recipe[servicec6624803-0018-4144-ad08-ec32c8be89c5::jar]") }
  notifies :delete, "file[#{node['tomcat']['webapp_dir']}/c6624803-0018-4144-ad08-ec32c8be89c5.war]", :immediately
end

ruby_block "remove-servicec6624803-0018-4144-ad08-ec32c8be89c5" do
  block do
  	node.run_list.remove("recipe[servicec6624803-0018-4144-ad08-ec32c8be89c5::war]")
  end
  only_if { node.run_list.include?("recipe[servicec6624803-0018-4144-ad08-ec32c8be89c5::war]") }
end

ruby_block "remove-deactivate-servicec6624803-0018-4144-ad08-ec32c8be89c5" do
  block do
    node.run_list.remove("recipe[deactivate-servicec6624803-0018-4144-ad08-ec32c8be89c5]")
  end
  only_if { node.run_list.include?("recipe[deactivate-servicec6624803-0018-4144-ad08-ec32c8be89c5]") }
end
