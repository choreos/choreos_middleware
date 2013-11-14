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

ruby_block "remove-serviceb98b4500-1184-4854-bc95-e47060115456" do
  block do
  	# do nothing!
  	i = 0
  end
  only_if { node.run_list.include?("recipe[serviceb98b4500-1184-4854-bc95-e47060115456::jar]") }
  notifies :delete, "file[#{node['tomcat']['webapp_dir']}/b98b4500-1184-4854-bc95-e47060115456.war]", :immediately
end

ruby_block "remove-serviceb98b4500-1184-4854-bc95-e47060115456" do
  block do
  	node.run_list.remove("recipe[serviceb98b4500-1184-4854-bc95-e47060115456::war]")
  end
  only_if { node.run_list.include?("recipe[serviceb98b4500-1184-4854-bc95-e47060115456::war]") }
end

ruby_block "remove-deactivate-serviceb98b4500-1184-4854-bc95-e47060115456" do
  block do
    node.run_list.remove("recipe[deactivate-serviceb98b4500-1184-4854-bc95-e47060115456]")
  end
  only_if { node.run_list.include?("recipe[deactivate-serviceb98b4500-1184-4854-bc95-e47060115456]") }
end
