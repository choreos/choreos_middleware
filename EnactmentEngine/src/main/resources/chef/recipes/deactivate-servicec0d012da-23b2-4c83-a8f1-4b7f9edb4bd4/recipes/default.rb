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

ruby_block "remove-servicec0d012da-23b2-4c83-a8f1-4b7f9edb4bd4" do
  block do
  	# do nothing!
  	i = 0
  end
  only_if { node.run_list.include?("recipe[servicec0d012da-23b2-4c83-a8f1-4b7f9edb4bd4::jar]") }
  notifies :delete, "file[#{node['tomcat']['webapp_dir']}/c0d012da-23b2-4c83-a8f1-4b7f9edb4bd4.war]", :immediately
end

ruby_block "remove-servicec0d012da-23b2-4c83-a8f1-4b7f9edb4bd4" do
  block do
  	node.run_list.remove("recipe[servicec0d012da-23b2-4c83-a8f1-4b7f9edb4bd4::war]")
  end
  only_if { node.run_list.include?("recipe[servicec0d012da-23b2-4c83-a8f1-4b7f9edb4bd4::war]") }
end

ruby_block "remove-deactivate-servicec0d012da-23b2-4c83-a8f1-4b7f9edb4bd4" do
  block do
    node.run_list.remove("recipe[deactivate-servicec0d012da-23b2-4c83-a8f1-4b7f9edb4bd4]")
  end
  only_if { node.run_list.include?("recipe[deactivate-servicec0d012da-23b2-4c83-a8f1-4b7f9edb4bd4]") }
end
