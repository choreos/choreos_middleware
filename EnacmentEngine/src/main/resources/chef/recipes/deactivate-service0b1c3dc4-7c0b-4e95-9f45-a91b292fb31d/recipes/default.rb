#
# Cookbook Name:: generic-jar-service-template
# Recipe:: default
#
# Copyright 2012, YOUR_COMPANY_NAME
#
# All rights reserved - Do Not Redistribute
#

##########################################################################
#                  #
#                IMPORTANT DEVELOPMENT NOTICE:                           #
#                  #
# All ocurrences of $ NAME must be replaced with the actual service name #
#            before uploading the recipe to the chef-server              #
#                  #
##########################################################################

ruby_block "disable-service0b1c3dc4-7c0b-4e95-9f45-a91b292fb31d" do
  block do
  	# do nothing!
  	i = 0
  end
  only_if { node.run_list.include?("recipe[service0b1c3dc4-7c0b-4e95-9f45-a91b292fb31d::jar]") }
  notifies :delete, "file[#{node['CHOReOSData']['serviceData']['0b1c3dc4-7c0b-4e95-9f45-a91b292fb31d']['InstallationDir']}/service-0b1c3dc4-7c0b-4e95-9f45-a91b292fb31d.jar]", :immediately
end

ruby_block "remove-service0b1c3dc4-7c0b-4e95-9f45-a91b292fb31d" do
  block do
  	node.run_list.remove("recipe[service0b1c3dc4-7c0b-4e95-9f45-a91b292fb31d::jar]")
  end
  only_if { node.run_list.include?("recipe[service0b1c3dc4-7c0b-4e95-9f45-a91b292fb31d::jar]") }
end

ruby_block "remove-deactivate-service0b1c3dc4-7c0b-4e95-9f45-a91b292fb31d" do
  block do
    node.run_list.remove("recipe[deactivate-service0b1c3dc4-7c0b-4e95-9f45-a91b292fb31d]")
  end
  only_if { node.run_list.include?("recipe[deactivate-service0b1c3dc4-7c0b-4e95-9f45-a91b292fb31d]") }
end
