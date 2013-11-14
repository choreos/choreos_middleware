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

#ruby_block "disable-servicebf464a17-7c13-4962-b3ff-02238583008d" do
#  block do
#  	# do nothing!
#  	i = 0
#  end
#  only_if { node.run_list.include?("recipe[servicebf464a17-7c13-4962-b3ff-02238583008d::jar]") }
#  notifies :delete, "file[#{node['CHOReOSData']['serviceData']['bf464a17-7c13-4962-b3ff-02238583008d']['InstallationDir']}/service-bf464a17-7c13-4962-b3ff-02238583008d.jar]", :immediately
#end

ruby_block "remove-servicebf464a17-7c13-4962-b3ff-02238583008d" do
  block do
  	node.run_list.remove("recipe[servicebf464a17-7c13-4962-b3ff-02238583008d::jar]")
  end
  only_if { node.run_list.include?("recipe[servicebf464a17-7c13-4962-b3ff-02238583008d::jar]") }
end

ruby_block "remove-deactivate-servicebf464a17-7c13-4962-b3ff-02238583008d" do
  block do
    node.run_list.remove("recipe[deactivate-servicebf464a17-7c13-4962-b3ff-02238583008d]")
  end
  only_if { node.run_list.include?("recipe[deactivate-servicebf464a17-7c13-4962-b3ff-02238583008d]") }
end
