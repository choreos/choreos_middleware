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

ruby_block "disable-service286cd3ad-e246-4496-98c9-0d5361b170dd" do
  block do
  	# do nothing!
  	i = 0
  end
  only_if { node.run_list.include?("recipe[service286cd3ad-e246-4496-98c9-0d5361b170dd::jar]") }
  notifies :delete, "file[#{node['CHOReOSData']['serviceData']['286cd3ad-e246-4496-98c9-0d5361b170dd']['InstallationDir']}/service-286cd3ad-e246-4496-98c9-0d5361b170dd.jar]", :immediately
end

ruby_block "remove-service286cd3ad-e246-4496-98c9-0d5361b170dd" do
  block do
  	node.run_list.remove("recipe[service286cd3ad-e246-4496-98c9-0d5361b170dd::jar]")
  end
  only_if { node.run_list.include?("recipe[service286cd3ad-e246-4496-98c9-0d5361b170dd::jar]") }
end

ruby_block "remove-deactivate-service286cd3ad-e246-4496-98c9-0d5361b170dd" do
  block do
    node.run_list.remove("recipe[deactivate-service286cd3ad-e246-4496-98c9-0d5361b170dd]")
  end
  only_if { node.run_list.include?("recipe[deactivate-service286cd3ad-e246-4496-98c9-0d5361b170dd]") }
end
