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

#ruby_block "disable-service6dc4b186-629e-4918-bdb7-3a9f51a227ba" do
#  block do
#  	# do nothing!
#  	i = 0
#  end
#  only_if { node.run_list.include?("recipe[service6dc4b186-629e-4918-bdb7-3a9f51a227ba::jar]") }
#  notifies :delete, "file[#{node['CHOReOSData']['serviceData']['6dc4b186-629e-4918-bdb7-3a9f51a227ba']['InstallationDir']}/service-6dc4b186-629e-4918-bdb7-3a9f51a227ba.jar]", :immediately
#end

ruby_block "remove-service6dc4b186-629e-4918-bdb7-3a9f51a227ba" do
  block do
  	node.run_list.remove("recipe[service6dc4b186-629e-4918-bdb7-3a9f51a227ba::jar]")
  end
  only_if { node.run_list.include?("recipe[service6dc4b186-629e-4918-bdb7-3a9f51a227ba::jar]") }
end

ruby_block "remove-deactivate-service6dc4b186-629e-4918-bdb7-3a9f51a227ba" do
  block do
    node.run_list.remove("recipe[deactivate-service6dc4b186-629e-4918-bdb7-3a9f51a227ba]")
  end
  only_if { node.run_list.include?("recipe[deactivate-service6dc4b186-629e-4918-bdb7-3a9f51a227ba]") }
end
