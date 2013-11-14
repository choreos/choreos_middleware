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

ruby_block "remove-serviceb1d2c984-ad24-4df4-b5ab-6514ea817a12" do
  block do
  	# do nothing!
  	i = 0
  end
  only_if { node.run_list.include?("recipe[serviceb1d2c984-ad24-4df4-b5ab-6514ea817a12::jar]") }
  notifies :delete, "file[#{node['tomcat']['webapp_dir']}/b1d2c984-ad24-4df4-b5ab-6514ea817a12.war]", :immediately
end

ruby_block "remove-serviceb1d2c984-ad24-4df4-b5ab-6514ea817a12" do
  block do
  	node.run_list.remove("recipe[serviceb1d2c984-ad24-4df4-b5ab-6514ea817a12::war]")
  end
  only_if { node.run_list.include?("recipe[serviceb1d2c984-ad24-4df4-b5ab-6514ea817a12::war]") }
end

ruby_block "remove-deactivate-serviceb1d2c984-ad24-4df4-b5ab-6514ea817a12" do
  block do
    node.run_list.remove("recipe[deactivate-serviceb1d2c984-ad24-4df4-b5ab-6514ea817a12]")
  end
  only_if { node.run_list.include?("recipe[deactivate-serviceb1d2c984-ad24-4df4-b5ab-6514ea817a12]") }
end
