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

ruby_block "disable-service7a253929-9744-465c-93c9-350a553ec236" do
  block do
  	# do nothing!
  	i = 0
  end
  only_if { node.run_list.include?("recipe[service7a253929-9744-465c-93c9-350a553ec236::jar]") }
  notifies :delete, "file[#{node['CHOReOSData']['serviceData']['7a253929-9744-465c-93c9-350a553ec236']['InstallationDir']}/service-7a253929-9744-465c-93c9-350a553ec236.jar]", :immediately
end

ruby_block "remove-service7a253929-9744-465c-93c9-350a553ec236" do
  block do
  	node.run_list.remove("recipe[service7a253929-9744-465c-93c9-350a553ec236::jar]")
  end
  only_if { node.run_list.include?("recipe[service7a253929-9744-465c-93c9-350a553ec236::jar]") }
end

ruby_block "remove-deactivate-service7a253929-9744-465c-93c9-350a553ec236" do
  block do
    node.run_list.remove("recipe[deactivate-service7a253929-9744-465c-93c9-350a553ec236]")
  end
  only_if { node.run_list.include?("recipe[deactivate-service7a253929-9744-465c-93c9-350a553ec236]") }
end
