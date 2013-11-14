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

ruby_block "disable-serviced4450346-58f9-4bee-be33-fa85ca9542bb" do
  block do
  	# do nothing!
  	i = 0
  end
  only_if { node.run_list.include?("recipe[serviced4450346-58f9-4bee-be33-fa85ca9542bb::jar]") }
  notifies :delete, "file[#{node['CHOReOSData']['serviceData']['d4450346-58f9-4bee-be33-fa85ca9542bb']['InstallationDir']}/service-d4450346-58f9-4bee-be33-fa85ca9542bb.jar]", :immediately
end

ruby_block "remove-serviced4450346-58f9-4bee-be33-fa85ca9542bb" do
  block do
  	node.run_list.remove("recipe[serviced4450346-58f9-4bee-be33-fa85ca9542bb::jar]")
  end
  only_if { node.run_list.include?("recipe[serviced4450346-58f9-4bee-be33-fa85ca9542bb::jar]") }
end

ruby_block "remove-deactivate-serviced4450346-58f9-4bee-be33-fa85ca9542bb" do
  block do
    node.run_list.remove("recipe[deactivate-serviced4450346-58f9-4bee-be33-fa85ca9542bb]")
  end
  only_if { node.run_list.include?("recipe[deactivate-serviced4450346-58f9-4bee-be33-fa85ca9542bb]") }
end
