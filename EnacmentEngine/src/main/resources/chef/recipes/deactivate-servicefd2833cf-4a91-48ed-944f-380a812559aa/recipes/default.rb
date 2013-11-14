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

ruby_block "remove-servicefd2833cf-4a91-48ed-944f-380a812559aa" do
  block do
  	# do nothing!
  	i = 0
  end
  only_if { node.run_list.include?("recipe[servicefd2833cf-4a91-48ed-944f-380a812559aa::jar]") }
  notifies :delete, "file[#{node['tomcat']['webapp_dir']}/fd2833cf-4a91-48ed-944f-380a812559aa.war]", :immediately
end

ruby_block "remove-servicefd2833cf-4a91-48ed-944f-380a812559aa" do
  block do
  	node.run_list.remove("recipe[servicefd2833cf-4a91-48ed-944f-380a812559aa::war]")
  end
  only_if { node.run_list.include?("recipe[servicefd2833cf-4a91-48ed-944f-380a812559aa::war]") }
end

ruby_block "remove-deactivate-servicefd2833cf-4a91-48ed-944f-380a812559aa" do
  block do
    node.run_list.remove("recipe[deactivate-servicefd2833cf-4a91-48ed-944f-380a812559aa]")
  end
  only_if { node.run_list.include?("recipe[deactivate-servicefd2833cf-4a91-48ed-944f-380a812559aa]") }
end
