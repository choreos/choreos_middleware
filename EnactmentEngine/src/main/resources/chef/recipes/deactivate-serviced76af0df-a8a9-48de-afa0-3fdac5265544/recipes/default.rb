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

ruby_block "disable-serviced76af0df-a8a9-48de-afa0-3fdac5265544" do
  block do
  	# do nothing!
  	i = 0
  end
  only_if { node.run_list.include?("recipe[serviced76af0df-a8a9-48de-afa0-3fdac5265544::jar]") }
  notifies :delete, "file[#{node['CHOReOSData']['serviceData']['d76af0df-a8a9-48de-afa0-3fdac5265544']['InstallationDir']}/service-d76af0df-a8a9-48de-afa0-3fdac5265544.jar]", :immediately
end

ruby_block "remove-serviced76af0df-a8a9-48de-afa0-3fdac5265544" do
  block do
  	node.run_list.remove("recipe[serviced76af0df-a8a9-48de-afa0-3fdac5265544::jar]")
  end
  only_if { node.run_list.include?("recipe[serviced76af0df-a8a9-48de-afa0-3fdac5265544::jar]") }
end

ruby_block "remove-deactivate-serviced76af0df-a8a9-48de-afa0-3fdac5265544" do
  block do
    node.run_list.remove("recipe[deactivate-serviced76af0df-a8a9-48de-afa0-3fdac5265544]")
  end
  only_if { node.run_list.include?("recipe[deactivate-serviced76af0df-a8a9-48de-afa0-3fdac5265544]") }
end
