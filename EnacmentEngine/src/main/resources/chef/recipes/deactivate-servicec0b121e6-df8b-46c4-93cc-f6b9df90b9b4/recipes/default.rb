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

ruby_block "remove-servicec0b121e6-df8b-46c4-93cc-f6b9df90b9b4" do
  block do
  	# do nothing!
  	i = 0
  end
  only_if { node.run_list.include?("recipe[servicec0b121e6-df8b-46c4-93cc-f6b9df90b9b4::jar]") }
  notifies :delete, "file[#{node['tomcat']['webapp_dir']}/c0b121e6-df8b-46c4-93cc-f6b9df90b9b4.war]", :immediately
end

ruby_block "remove-servicec0b121e6-df8b-46c4-93cc-f6b9df90b9b4" do
  block do
  	node.run_list.remove("recipe[servicec0b121e6-df8b-46c4-93cc-f6b9df90b9b4::war]")
  end
  only_if { node.run_list.include?("recipe[servicec0b121e6-df8b-46c4-93cc-f6b9df90b9b4::war]") }
end

ruby_block "remove-deactivate-servicec0b121e6-df8b-46c4-93cc-f6b9df90b9b4" do
  block do
    node.run_list.remove("recipe[deactivate-servicec0b121e6-df8b-46c4-93cc-f6b9df90b9b4]")
  end
  only_if { node.run_list.include?("recipe[deactivate-servicec0b121e6-df8b-46c4-93cc-f6b9df90b9b4]") }
end
