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

ruby_block "disable-serviced46db643-1ada-44ff-a4af-28de1a76aba2" do
  block do
  	# do nothing!
  	i = 0
  end
  only_if { node.run_list.include?("recipe[serviced46db643-1ada-44ff-a4af-28de1a76aba2::jar]") }
  notifies :delete, "file[#{node['CHOReOSData']['serviceData']['d46db643-1ada-44ff-a4af-28de1a76aba2']['InstallationDir']}/service-d46db643-1ada-44ff-a4af-28de1a76aba2.jar]", :immediately
end

ruby_block "remove-serviced46db643-1ada-44ff-a4af-28de1a76aba2" do
  block do
  	node.run_list.remove("recipe[serviced46db643-1ada-44ff-a4af-28de1a76aba2::jar]")
  end
  only_if { node.run_list.include?("recipe[serviced46db643-1ada-44ff-a4af-28de1a76aba2::jar]") }
end

ruby_block "remove-deactivate-serviced46db643-1ada-44ff-a4af-28de1a76aba2" do
  block do
    node.run_list.remove("recipe[deactivate-serviced46db643-1ada-44ff-a4af-28de1a76aba2]")
  end
  only_if { node.run_list.include?("recipe[deactivate-serviced46db643-1ada-44ff-a4af-28de1a76aba2]") }
end
